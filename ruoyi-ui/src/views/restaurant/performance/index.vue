<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['restaurant:performance:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['restaurant:performance:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['restaurant:performance:remove']">删除</el-button>
      </el-col>
      <!-- <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar> -->
    </el-row>

    <el-table v-if="refreshTable" v-loading="loading" :data="tPerformanceList" row-key="id"
      :default-expand-all="isExpandAll" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column prop="performanceName" label="绩效名称" width="170" align="center"></el-table-column>
      <el-table-column prop="performancePosition" label="绩效规则（盘/天）" width="170" align="center"></el-table-column>
      <el-table-column prop="performancePay" label="绩效价格" width="170" align="center"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="170" align="center"></el-table-column>
      <el-table-column prop="updateTime" label="更新时间" width="170" align="center"></el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['restaurant:performance:edit']">修改</el-button>
          <el-button v-if="scope.row.parentId != 0" size="mini" type="text" icon="el-icon-delete"
            @click="handleDelete(scope.row)" v-hasPermi="['restaurant:performance:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改菜单配置对话框 -->
    <el-dialog title="绩效管理" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="档位命名" prop="performanceName">
          <el-input v-model="form.performanceName" placeholder="请输入档位名称" />
        </el-form-item>
        <el-form-item label="绩效规则" prop="performancePosition">
          <el-input-number v-model="form.performancePosition" placeholder="超过菜品量(盘/天)"></el-input-number>
        </el-form-item>
        <el-form-item label="绩效价格" prop="performancePay">
          <el-input-number v-model="form.performancePay" placeholder="绩效价格" :precision='1'></el-input-number>
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

import { add, list, selectTPerformanceListById, edit ,remove} from "@/api/restaurant/performance"

export default {
  components: {},
  props: [],
  data() {
    return {
      total: 0,
      add: true,
      open: false,
      refreshTable: true,
      loading: true,
      tPerformanceList: [],
      single: true,
      multiple: true,
      total: 0,
      performanceNames: [],
      ids: [],
      dateRange: [],
      isExpandAll: true,
      form: {},
      queryParams: {
        tenantId: 1,
        pageNum: 1,
        pageSize: 10,
        createTime: undefined
      },
      rules: {
        performanceName: [
          { required: true, message: "档位名称不能为空", trigger: "blur" }
        ],
        performancePosition: [
          { required: true, message: "绩效规则不能为空", trigger: "blur" }
        ],
        performancePay: [
          { required: true, message: "绩效价格不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      list(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.tPerformanceList = response.rows
        this.loading = false
        this.total = response.total
      })
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.performanceNames = selection.map(item => item.performanceName)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.add = true
      this.reset()
      this.open = true
    },
    handleUpdate(row) {
      this.add = false
      this.reset()
      this.open = true
      const id = row.id || this.ids
      selectTPerformanceListById(id).then(res => {
        this.form = res.data[0]
      })
    },
    handleDelete(row) {
      var that = this
      const ids = row.id || this.ids
      const performanceNames = row.performanceName || this.performanceNames
      if (row.id == 0) {
        ids.push(0)
      }
      var str = ""
      var listOrInt = 1
      for (var i = 0; i < ids.length; i++) {
        if (i == ids.length - 1) {
          str = str + performanceNames[i]
        } else {
          str = str + performanceNames[i] + ","
        }
      }
      if (str == "") {
        listOrInt = 0
        str = performanceNames
      }
      this.$modal.confirm('是否确认删除档位名字为"' + str + '"的数据项？').then(function () {
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
              resStr = resStr + res.data.ids[i].dishName + ",";
            }
            that.$modal.msgError("删除" + resStr + "失败")
          }
          that.getList()
        })
      })
    },
    submitForm() {
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
    reset() {
      this.form = {
        performanceName: undefined,
        performancePosition: undefined,
        performancePay: undefined,
        isState: undefined
      };
      this.resetForm("form");
    },
  }
}

</script>
<style></style>
