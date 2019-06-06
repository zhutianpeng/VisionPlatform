var PoseMatch = {
    template:``,
    data: function(){
        return{
            stdPoseList:[], //标准姿态数据缓存区（一次性写好）
            realPoseList:[], //实时姿态数据缓存区（实时变动）
            haveHeader: false, //用于标志是否找到实时姿态存储的起点
            stdCount:'', //标准动作帧数
            floatRange: 10, //帧数浮动范围
            threshold:'', //匹配度阈值，超出此阈值可认为动作匹配成功
        }
    },
    props:['realPose'],
    mounted:function(){
        this.getStandardData();
        this.stdCount = this.stdPoseList.length;

    },
    watch:{
        realPose:{ //对传入的单帧实时姿态数据进行监听
            deep: true,
            handler: function (newVal) {
                if(this.poseListCount === 0){
                    this.findStarter(newVal);
                }else{
                    this.realPoseList.push(newVal); //找到起点之后，传入的数据都往缓存区中填充
                }
            }
        },
        poseListCount: function (count) {
            if(count >= this.stdCount - this.floatRange){ //超出了起始点，则开始DTW识别
                if(this.dynamicTimeWarping(this.stdPoseList, this.realPoseList) >= this.threshold){ //动作匹配成功
                    console.log("单次动作匹配完成，计数+1，开始下一次动作匹配！");
                    this.realPoseList = []; //清空缓存区
                }else if(count >= this.stdCount + this.floatRange){ //动作没匹配成功并且超越了结束点
                    console.log("单次动作匹配不成功，不计数，开始下一次动作匹配！")
                    this.realPoseList = []; //清空缓存区
                }
            }
        }
    },
    computed:{
        poseListCount:function () { //统计实时姿态缓存区的数据量
            return this.realPoseList.length;
        }
    },
    methods:{
        getStandardData:function(){ //要从数据库中拉取标准的姿态数据作为匹配对象 TODO

        },
        findStarter:function(poseData){ //确定实时姿态存储的起点
            if(this.getDistance(poseData, this.stdPoseList[0]) > 0.5){
                this.realPoseList.push(newVal); //找到了起点

            }
        },
        dynamicTimeWarping:function (stdPoseList, realPoseList) { //标准姿态数据和采集姿态数据进行匹配
            let matrix=[];
            let matrix2=[];
            for(let i = 0; i < stdPoseList.length; i++){
                let rowArray = []; //矩阵每一行元素所组成的数组
                for(let j = 0; j < realPoseList.length; j++){
                    rowArray.push(this.getDistance(stdPoseList[i], realPoseList[j]));
                }
                matrix.push(rowArray);
                matrix2.push(new Array(realPoseList.length)); //预先留好位置
            }

            for(let i = 0; i < stdPoseList.length; i++){
                for(let j = 0; j < realPoseList.length; j++){
                    if(i === 0){ //第一行的情况单独处理
                        matrix2[0][j]=matrix[0][j];
                    }else{
                        if(j === 0){ //第一列的情况也单独处理
                            matrix2[i][0] = matrix[i][0];
                        }else{
                            let d = matrix[i][j];
                            matrix2[i][j] = Math.min(matrix2[i][j-1], matrix2[i-1][j], matrix2[i-1][j-1]) + d;
                        }
                    }
                }
            }
            return matrix2[arr1.length-1][arr2.length-1];
        },
        getDistance:function (stdSingleFrame, realSingleFrame) { //单帧姿态数据之间求距离
            let average;
            let count = 0;
            for(let index in realSingleFrame){
                let vector1 = [realSingleFrame[index].x, realSingleFrame[index].y]; //TODO 这样算还不太对
                let vector2 = [stdSingleFrame[index].x, stdSingleFrame[index].y];
                count ++;
                average += this.getCosSimi(vector1, vector2);
            }
            return average/count;
        },
        getCosSimi:function (vector1, vector2) { //计算两向量的余弦相似度
            if(vector1.length !== vector2.length){
                return 0;
            }
            let sumTotal = 0;
            let sum1 = 0;
            let sum2 = 0;
            for(let i = 0; i < vector1.length; i++){
                sumTotal += vector1[i] * vector2[i];
                sum1 += Math.pow(vector1[i], 2);
                sum2 += Math.pow(vector2[i], 2);
            }
            return (sumTotal/(Math.sqrt(sum1) * Math.sqrt(sum2)) + 1)/2; //最终返回结果在0到1之间
        }
    }
};