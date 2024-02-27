<template>
    <div id="echart-category" :style="{ width: '100%', height: '100%' }"></div>
</template>

<script>
import * as echarts from 'echarts'
export default {
    data() {
        return {

        }
    },
    methods: {
        initChart(name, xData, yData, color) {
            let getchart = echarts.getInstanceByDom(document.getElementById('echart-category'));
            if (getchart == null) {
                getchart = echarts.init(document.getElementById('echart-category'));
            }
            var option = {
                // color:'#4fb943',
                color: color,
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: [name],
                    bottom: '0%'
                },
                grid: { //调整图表上下左右位置
                    top: '10%',
                    left: '3%',
                    right: '8%',
                    bottom: '11%',
                    containLabel: true
                },
                textStyle: {
                    color: "#000000"
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: xData,
                },
                yAxis: {
                    type: 'value',
                },
                series: [
                    {
                        name: name,
                        type: 'line',
                        stack: '总量',
                        data: yData
                    },
                ]
            };

            getchart.setOption(option);
            //随着屏幕大小调节图表
            window.addEventListener("resize", () => {
                getchart.resize();
            });
        },
    }
}
</script>

<style></style>