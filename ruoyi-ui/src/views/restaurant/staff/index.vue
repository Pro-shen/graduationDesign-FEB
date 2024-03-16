<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
            <el-form-item label="员工名称" prop="userName">
                <el-input v-model="queryParams.userName" placeholder="请输入员工名称" clearable style="width: 240px"
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                    v-hasPermi="['restaurant:staff:add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                    v-hasPermi="['restaurant:staff:edit']">修改</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                    @click="handleDelete" v-hasPermi="['restaurant:staff:remove']">删除</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-if="refreshTable" v-loading="loading" :data="tUserRestaurantList" row-key="id"
            :default-expand-all="isExpandAll" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column prop="userName" label="工人名称" width="170"></el-table-column>
            <el-table-column prop="restaurantName" label="餐厅名称" width="170"></el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="170"></el-table-column>
            <el-table-column prop="updateTime" label="更新时间" width="170"></el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                        v-hasPermi="['restaurant:staff:edit']">修改</el-button>
                    <el-button v-if="scope.row.parentId != 0" size="mini" type="text" icon="el-icon-delete"
                        @click="handleDelete(scope.row)" v-hasPermi="['restaurant:staff:remove']">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize" @pagination="getList" />

        <el-dialog title="工人餐厅管理" :visible.sync="open" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="100px">
                <el-form-item label="选择工人" prop="selectorUser">
                    <el-select v-model="form.userId" placeholder="请选择工人">
                        <el-option v-for="item in form.optionsUser" :key="item.value" :label="item.label"
                            :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="选择餐厅" prop="selectorRestaurant">
                    <el-select v-model="form.restaurantId" placeholder="请选择餐厅">
                        <el-option v-for="item in form.optionsRestaurant" :key="item.value" :label="item.label"
                            :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
import { list, userList, restaurantList ,add} from "@/api/restaurant/staff"
export default {
    data() {
        return {
            add: true,
            open: false,
            showSearch: true,
            refreshTable: true,
            isExpandAll: true,
            queryParams: {
                tenantId: 1,
                pageNum: 1,
                pageSize: 10,
                userName: undefined,
            },
            showSearch: true,
            form: {
                number: 0,
                userId: undefined,
                optionsUser: [],
                restaurantId: undefined,
                optionsRestaurant: []
            },
            loading: true,
            total: 0,
            multiple: true,
            single: true,
            rules: {
                userId: [
                    { required: true, message: "选择工人不能为空", trigger: "blur" }
                ],
                restaurantId: [
                    { required: true, message: "选择餐厅不能为空", trigger: "blur" }
                ]
            },
            tUserRestaurantList: [],
        }
    },
    created() {
        this.getList()
    },
    methods: {
        getList() {
            this.loading = true;
            list(this.queryParams).then(response => {
                this.tUserRestaurantList = response.rows
                this.loading = false
                this.total = response.total
            })
        },
        getUserList() {
            userList(this.queryParams).then(res => {
                var list = []
                for (var i = 0; i < res.length; i++) {
                    list.push({
                        value: res[i].userId,
                        label: res[i].nickName
                    })
                }
                this.form.optionsUser = list
            })
        },
        getRestaurantList() {
            restaurantList(this.queryParams).then(res => {
                var list = []
                for (var i = 0; i < res.length; i++) {
                    list.push({
                        value: res[i].id,
                        label: res[i].restaurantName
                    })
                }
                this.form.optionsRestaurant = list
            })
        },
        handleAdd() {
            this.reset()
            this.add = true
            this.open = true
        },
        submitForm() {
            this.form.isState = 1
            this.$refs["form"].validate(valid => {
                if (valid) {
                    if (this.add) {
                        console.log(this.form)
                        add(this.form).then(res => {
                            if (res.data == 0) {
                                this.$modal.msgError("添加失败")
                            } else if (res.data == 1) {
                                this.$modal.msgSuccess("添加成功")
                                this.getList();
                            }
                        })
                    } else {
                        edit(this.form).then(res => {
                            if (res.data == 0) {
                                this.$modal.msgError("修改失败")
                            } else if (res.data == 1) {
                                this.$modal.msgSuccess("修改成功")
                                this.getList();
                            }
                        })
                    }
                } else {
                    this.$modal.msgError("请填写正确的信息")
                }
            })
            this.open = false
        },
        cancel() {
            this.open = false
        },
        reset() {
            this.form = {
                number: 0,
                userId: undefined,
                optionsUser: [],
                restaurantId: undefined,
                optionsRestaurant: []
            };
            this.getUserList()
            this.getRestaurantList()
            this.resetForm("form");
        },
        handleQuery() {

        },
        resetQuery() {

        },
        handleSelectionChange(selection) {

        },
        handleUpdate(row) {

        },
        handleDelete(row) {

        },
        handleExport() {

        }
    }
}
</script>

<style></style>