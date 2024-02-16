<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['restaurant:menu:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['restaurant:menu:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['restaurant:menu:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['restaurant:menu:export']">导出</el-button>
      </el-col>
      <!-- <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar> -->
    </el-row>

    <el-table v-if="refreshTable" v-loading="loading" :data="tPriceList" row-key="id" :default-expand-all="isExpandAll"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="盘子颜色" width="200" align="center">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.t_plate_color" :value="scope.row.plateColor" />
        </template>
      </el-table-column>
      <!-- <el-table-column prop="plateColor" label="盘子颜色" width="200"></el-table-column> -->
      <el-table-column prop="platePrice" label="盘子价格" width="200"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
      <el-table-column prop="updateTime" label="更新时间" width="180"></el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['restaurant:menu:edit']">修改</el-button>
          <el-button v-if="scope.row.parentId != 0" size="mini" type="text" icon="el-icon-delete"
            @click="handleDelete(scope.row)" v-hasPermi="['restaurant:menu:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改菜单配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="盘子颜色" prop="plateColor">
          <el-select v-model="form.plateColor" placeholder="请选择盘子颜色">
            <el-option v-for="dict in dict.type.t_plate_color" :key="dict.value" :label="dict.label"
              :value="Number(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="盘子价格" prop="platePrice">
          <el-input v-model="form.platePrice" placeholder="请输入盘子价格" />
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
import { listPrice, addPrice, selectTFixPriceListById, edit, remove } from "@/api/restaurant/fixPrice"

export default {
  dicts: ['t_plate_color'],
  data() {
    return {
      single: true,
      multiple: true,
      ids: [],
      idsplateColor:[],
      add: true,
      loading: true,
      showSearch: true,
      refreshTable: true,
      isExpandAll: true,
      open: false,
      tPriceList: [],
      queryParams: {
        tenantId: 1,
        pageNum: 1,
        pageSize: 10,
        dishName: ''
      },
      form: {},
      rules: {
        plateColor: [
          { required: true, message: "盘子颜色不能为空", trigger: "blur" }
        ],
        platePrice: [
          {
            required: true,
            message: "请输入正确的价格(整数)",
            trigger: "blur",
            pattern: /^-?[1-9]\d*$/,
          }
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
      listPrice(this.queryParams).then(response => {
        this.tPriceList = response.rows
        this.loading = false;
      })
    },
    submitForm: function () {
      this.form.isState = 1
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.add) {
            addPrice(this.form).then(res => {
              if (res.data == 0) {
                this.$modal.msgError("添加失败")
              } else if (res.data == 1) {
                this.$modal.msgSuccess("添加成功")
                this.getList();
              } else if (res.data == 2) {
                this.$modal.msgError("改颜色的盘子价格已存在")
              }
            })
          } else {
            edit(this.form).then(res => {
              console.log(res.data)
              if (res.data == 0) {
                this.$modal.msgError("修改失败")
              } else if (res.data == 1) {
                this.$modal.msgSuccess("修改成功")
                this.getList();
              } else if (res.data == 2) {
                this.$modal.msgError("已存在该颜色的盘子")
              }
            })
          }
        }else{
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
      this.reset();
      const id = row.id || this.ids
      selectTFixPriceListById(id).then(res => {
        this.form = res.data[0]
      })
      this.open = true
    },
    reset() {
      this.form = {
        plateColor: undefined,
        platePrice: undefined,
        isState: undefined
      };
      this.resetForm("form");
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.idsplateColor = selection.map(item => item.plateColor)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    handleDelete(row) {
      var that = this
      const ids = row.plateColor || this.idsplateColor;
      if(row.plateColor == 0){
        ids.push(0)
      }
      var str = ""
      var listOrInt = 1
      for (var i = 0; i < ids.length; i++) {
        if (i == ids.length - 1) {
          str = str + this.dict.type.t_plate_color[ids[i]].label
        } else {
          str = str + this.dict.type.t_plate_color[ids[i]].label + ","
        }
      }
      if (str == "") {
        listOrInt = 0
        str = this.dict.type.t_plate_color[ids].label
      }
      this.$modal.confirm('是否确认删除盘子颜色为"' + str + '"的数据项？').then(function () {
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
          if(res.data.ids.length == 0){
            that.$modal.msgSuccess("删除成功")
          }else{
            var resStr = ""
            for(i=0;i<res.data.ids.length;i++){
              resStr = resStr + this.dict.type.t_plate_color[res.data.ids[i]]+",";
            }
            that.$modal.msgError("删除"+resStr+"失败")
          }
          that.getList()
        })
      })
    },
    handleExport() {
      this.download('/restaurant/fixPrice/export', {
        ...this.queryParams
      }, `Price_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style></style>