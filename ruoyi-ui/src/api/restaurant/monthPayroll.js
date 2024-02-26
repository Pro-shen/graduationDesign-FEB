import request from "@/utils/request";

export function listTPayroll(query) {
  return request({
    url: "/restaurant/monthPayroll/list",
    method: "get",
    params: query,
  });
}

export function add(data) {
  return request({
    url: "/restaurant/monthPayroll/add",
    method: "post",
    data: data,
  });
}

export function selectTPayrollListById(id) {
  return request({
    url: "/restaurant/monthPayroll/selectTPayrollListById/" + id,
    method: "get",
  });
}

export function edit(data) {
  return request({
    url: "/restaurant/monthPayroll/edit",
    method: "post",
    data: data,
  });
}

export function remove(data) {
  return request({
    url: "/restaurant/monthPayroll/remove",
    method: "post",
    data: data,
  });
}
