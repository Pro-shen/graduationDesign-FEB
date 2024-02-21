import request from "@/utils/request";
import { parseStrEmpty } from "@/utils/ruoyi";

export function list(query) {
  return request({
    url: "/restaurant/performance/list",
    method: "get",
    params: query,
  });
}

export function add(data) {
  return request({
    url: "/restaurant/performance/add",
    method: "post",
    data: data,
  });
}

export function selectTPerformanceListById(id) {
  return request({
    url: "/restaurant/performance/selectTPerformanceListById/" + id,
    method: "get",
  });
}

export function edit(data) {
  return request({
    url: "/restaurant/performance/edit",
    method: "post",
    data: data,
  });
}

export function remove(data) {
  return request({
    url: "/restaurant/performance/remove",
    method: "post",
    data: data,
  });
}
