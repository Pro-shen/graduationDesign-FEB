import request from "@/utils/request";

export function listTPayroll(query) {
    return request({
      url: "/restaurant/payroll/list",
      method: "get",
      params: query,
    });
  }