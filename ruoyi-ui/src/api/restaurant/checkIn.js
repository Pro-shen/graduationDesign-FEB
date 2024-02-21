import request from "@/utils/request";

export function add(data) {
  return request({
    url: "/restaurant/checkin/add",
    method: "post",
    data: data,
  });
}

export function list(data) {
  return request({
    url: "/restaurant/checkin/list",
    method: "post",
    data: data,
  });
}
