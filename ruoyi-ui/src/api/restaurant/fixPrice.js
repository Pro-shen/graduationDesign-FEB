import request from "@/utils/request";
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询菜单
export function listPrice(query) {
  return request({
    url: "/restaurant/fixPrice/list",
    method: "get",
    params: query,
  });
}

export function addPrice(data) {
  return request({
    url: '/restaurant/fixPrice/add',
    method: 'post',
    data: data
  })
}

export function selectTFixPriceListById(id){
  return request({
    url: '/restaurant/fixPrice/selectTFixPriceListById/'+id,
    method: 'get'
  })
}
