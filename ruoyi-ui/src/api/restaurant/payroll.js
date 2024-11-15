import request from "@/utils/request";

export function listTPayroll(query) {
  return request({
    url: "/restaurant/payroll/list",
    method: "get",
    params: query,
  });
}

export function add(data) {
  return request({
    url: "/restaurant/payroll/add",
    method: "post",
    data: data,
  });
}

export function selectTPayrollListById(id) {
  return request({
    url: "/restaurant/payroll/selectTPayrollListById/" + id,
    method: "get",
  });
}

export function edit(data) {
  return request({
    url: "/restaurant/payroll/edit",
    method: "post",
    data: data,
  });
}

export function remove(data) {
  return request({
    url: "/restaurant/payroll/remove",
    method: "post",
    data: data,
  });
}

export function selectTPayrollListByDay() {
  return request({
    url: "/restaurant/payroll/selectTPayrollListByDay",
    method: "post",
  });
}
