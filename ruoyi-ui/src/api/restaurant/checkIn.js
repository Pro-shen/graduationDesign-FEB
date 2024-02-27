import request from "@/utils/request";

export function add(data) {
  return request({
    url: "/restaurant/checkIn/add",
    method: "post",
    data: data,
  });
}

export function list(query) {
  return request({
    url: "/restaurant/checkIn/list",
    method: "get",
    params: query,
  });
}

