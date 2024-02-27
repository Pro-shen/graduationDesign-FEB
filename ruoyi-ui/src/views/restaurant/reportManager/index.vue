<template>
  <div class="app-container">
    <ChartsLintVue ref="chart_line_one" />
  </div>
</template>
  
<script>
import ChartsLintVue from '@/components/echarts/ChartsLint.vue';
export default {
  data() {
    return {
      weft_name: "测试",
      weft_xData: ["day1", "day2", "day3", "day4"],
      weft_yData: [1, 2, 3, 4]
    }
  },
  components: {
    ChartsLintVue
  },
  created() {
    setTimeout(() => {
      this.$refs.chart_line_one.initChart(this.weft_name, this.weft_xData, this.weft_yData,"#4fb943")
    }, 10);
  },
  methods: {
    
  },
}
</script>
  
<style></style>