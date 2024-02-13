import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询菜单
export function listPrice(query) {
    return request({
      url: '/restaurant/fixPrice/list',
      method: 'get',
      params: query
    })
  }