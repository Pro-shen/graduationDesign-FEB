import request from "@/utils/request";

export function list(query) {
    return request({
      url: "/restaurant/sale/list",
      method: "get",
      params: query,
    });
  }