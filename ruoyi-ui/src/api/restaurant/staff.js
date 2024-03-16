import request from "@/utils/request";

export function list(query) {
  return request({
    url: "/restaurant/staff/list",
    method: "get",
    params: query,
  });
}

export function userList(data) {
  return request({
    url: "/restaurant/staff/userList",
    method: "post",
    data: data,
  });
}

export function restaurantList(data) {
  return request({
    url: "/restaurant/staff/restaurantList",
    method: "post",
    data: data,
  });
}

export function add(data) {
  return request({
    url: "/restaurant/staff/add",
    method: "post",
    data: data,
  });
}
