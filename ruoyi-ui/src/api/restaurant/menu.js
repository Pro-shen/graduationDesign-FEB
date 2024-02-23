import request from "@/utils/request";
import { parseStrEmpty } from "@/utils/ruoyi";


export function listMenu(query) {
  return request({
    url: "/restaurant/menu/list",
    method: "get",
    params: query,
  });
}

export function listMenuTree(data) {
  return request({
    url: "/restaurant/menu/listTree",
    method: "post",
    data: data,
  });
}

export function addMenu(data) {
  return request({
    url: "/restaurant/menu/add",
    method: "post",
    data: data,
  });
}

export function selectTMenuListById(id) {
  return request({
    url: "/restaurant/menu/selectTMenuListById/" + id,
    method: "get",
  });
}

export function edit(data) {
  return request({
    url: "/restaurant/menu/edit",
    method: "post",
    data: data,
  });
}

export function remove(data) {
  return request({
    url: "/restaurant/menu/remove",
    method: "post",
    data: data,
  });
}
