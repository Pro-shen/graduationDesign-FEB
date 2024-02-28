<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
            <el-form-item label="菜品名称" prop="dishName">
                <el-input v-model="queryParams.dishName" placeholder="请输入菜品名称" clearable style="width: 240px"
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="创建时间">
                <el-date-picker v-model="dateRange" style="width: 240px" value-format="yyyy-MM-dd" type="daterange"
                    range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                    v-hasPermi="['restaurant:sale:export']">导出</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExportMonth"
                    v-hasPermi="['restaurant:sale:export']">导出本月</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExportQuarter"
                    v-hasPermi="['restaurant:sale:export']">导出本季度</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExportYear"
                    v-hasPermi="['restaurant:sale:export']">导出本年</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-if="refreshTable" v-loading="loading" :data="tSaleList" row-key="id" :default-expand-all="isExpandAll"
            :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
            <el-table-column prop="dishName" label="菜品名称" align="center" width="170"></el-table-column>
            <el-table-column prop="number" label="菜品销量" align="center" width="170"></el-table-column>
            <el-table-column prop="dishProfit" label="菜品利润" align="center" width="170"></el-table-column>
            <el-table-column prop="dishProfitTotal" label="总利润" align="center" width="170"></el-table-column>
            <el-table-column prop="createTime" label="创建时间" align="center" width="170"></el-table-column>
            <el-table-column prop="updateTime" label="更新时间" align="center"
                class-name="small-padding fixed-width"></el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
            @pagination="getList" />

    </div>
</template>

<script>
import { list } from "@/api/restaurant/sale"

export default {
    data() {
        return {
            total: 0,
            single: true,
            multiple: true,
            loading: true,
            showSearch: true,
            refreshTable: true,
            isExpandAll: true,
            open: false,
            ids: [],
            tSaleList: [],
            dateRange: [],
            queryParams: {
                tenantId: 1,
                pageNum: 1,
                pageSize: 10,
                dishName: undefined,
                createTime: undefined
            },
            form: {},
        }
    },
    created() {
        this.getList()
    },
    methods: {
        getList() {
            this.loading = true
            list(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
                this.tSaleList = response.rows
                this.loading = false
                this.total = response.total
            })
        },
        handleExport() {
            this.download('/restaurant/sale/export', {
                ...this.queryParams
            }, `销售报表_${new Date().getTime()}.xlsx`)
        },
        handleExportMonth() {
            this.download('/restaurant/sale/exportMonth', {
                ...this.queryParams
            }, `月销售报表_${new Date().getTime()}.xlsx`)
        },
        handleExportQuarter() {
            this.download('/restaurant/sale/exportQuarter', {
                ...this.queryParams
            }, `季度销售报表_${new Date().getTime()}.xlsx`)
        },
        handleExportYear() {
            this.download('/restaurant/sale/exportYear', {
                ...this.queryParams
            }, `年销售报表_${new Date().getTime()}.xlsx`)
        },
        handleQuery() {
            this.queryParams.pageNum = 1;
            this.getList()
        },
        resetQuery() {
            this.dateRange = [];
            this.resetForm("queryForm");
            this.queryParams.pageNum = 1;
            this.$refs.tables.sort(this.defaultSort.prop, this.defaultSort.order)
        }
    }
}
</script>

<style></style>