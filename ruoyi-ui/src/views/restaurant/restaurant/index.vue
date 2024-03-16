<template>
    <div class="app-container">
        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                    v-hasPermi="['restaurant:restaurant:add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                    v-hasPermi="['restaurant:restaurant:edit']">修改</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                    @click="handleDelete" v-hasPermi="['restaurant:restaurant:remove']">删除</el-button>
            </el-col>
        </el-row>

        <el-table v-if="refreshTable" v-loading="loading" :data="tRestaurantList" row-key="id"
            :default-expand-all="isExpandAll" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column prop="restaurantName" label="餐厅名字" width="200"></el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
            <el-table-column prop="updateTime" label="更新时间" width="180"></el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                        v-hasPermi="['restaurant:restaurant:edit']">修改</el-button>
                    <el-button v-if="scope.row.parentId != 0" size="mini" type="text" icon="el-icon-delete"
                        @click="handleDelete(scope.row)" v-hasPermi="['restaurant:restaurant:remove']">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize" @pagination="getList" />

        <!-- 添加或修改餐厅配置对话框 -->
        <el-dialog title="餐厅管理" :visible.sync="open" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="100px">
                <el-form-item label="餐厅名字" prop="restaurantName">
                    <el-input v-model="form.restaurantName" placeholder="请输入餐厅名字" />
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
import { list, add, selectTRestaurantListById, edit, remove } from "@/api/restaurant/restaurant"
export default {
    data() {
        return {
            single: true,
            multiple: true,
            ids: [],
            restaurantNames: [],
            total: 0,
            open: false,
            add: true,
            loading: true,
            refreshTable: true,
            isExpandAll: true,
            queryParams: {
                tenantId: 1,
                pageNum: 1,
                pageSize: 10,
                dishName: ''
            },
            tRestaurantList: [],
            form: {},
            rules: {
                restaurantName: [
                    { required: true, message: "餐厅名字不能为空", trigger: "blur" }
                ]
            }

        }
    },
    created() {
        this.getList();
    },
    methods: {
        getList() {
            this.loading = true;
            list(this.queryParams).then(response => {
                this.tRestaurantList = response.rows
                this.loading = false
                this.total = response.total
            })
        },
        submitForm: function () {
            this.form.isState = 1
            this.$refs["form"].validate(valid => {
                if (valid) {
                    if (this.add) {
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
        handleAdd() {
            this.add = true
            this.open = true
            this.reset()
        },
        handleUpdate(row) {
            this.add = false
            this.reset()
            const id = row.id || this.ids
            selectTRestaurantListById(id).then(res => {
                this.form = res.data[0]
            })
            this.open = true
        },
        reset() {
            this.form = {
                restaurantName: undefined,
                isState: undefined
            };
            this.resetForm("form");
        },
        handleSelectionChange(selection) {
            this.ids = selection.map(item => item.id)
            this.restaurantNames = selection.map(item => item.restaurantName)
            this.single = selection.length != 1
            this.multiple = !selection.length
        },
        handleDelete(row) {
            var that = this
            const ids = row.id || this.ids;
            const restaurantNames = row.restaurantName || this.restaurantNames;
            if (row.id == 0) {
                ids.push(0)
            }
            var str = ""
            var listOrInt = 1
            for (var i = 0; i < ids.length; i++) {
                if (i == ids.length - 1) {
                    str = str + restaurantNames[i]
                } else {
                    str = str + restaurantNames[i] + ","
                }
            }
            if (str == "") {
                listOrInt = 0
                str = restaurantNames
            }
            this.$modal.confirm('是否确认删除餐厅名字为"' + str + '"的数据项？').then(function () {
                // return delRole(roleIds);
                var data = {}
                if (listOrInt == 0) {
                    data = {
                        id: ids
                    }
                } else {
                    data = {
                        ids: ids
                    }
                }
                remove(data).then(res => {
                    if (res.data.ids.length == 0) {
                        that.$modal.msgSuccess("删除成功")
                    } else {
                        var resStr = ""
                        for (i = 0; i < res.data.ids.length; i++) {
                            resStr = resStr + res.data.ids[i].restaurantNames[i] + ",";
                        }
                        that.$modal.msgError("删除" + resStr + "失败")
                    }
                    that.getList()
                })
            })
        },
    }
}
</script>

<style></style>