import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询菜单
export function listMenu(query) {
    return request({
      url: '/restaurant/menu/list',
      method: 'get',
      params: query
    })
  }