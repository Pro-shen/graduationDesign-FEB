import request from "@/utils/request";

export function list(query) {
  return request({
    url: "/restaurant/restaurant/list",
    method: "get",
    params: query,
  });
}

export function add(data) {
  return request({
    url: "/restaurant/restaurant/add",
    method: "post",
    data: data,
  });
}

export function selectTRestaurantListById(id) {
  return request({
    url: "/restaurant/restaurant/selectTRestaurantListById/" + id,
    method: "get",
  });
}

export function edit(data) {
  return request({
    url: "/restaurant/restaurant/edit",
    method: "post",
    data: data,
  });
}

export function remove(data) {
  return request({
    url: "/restaurant/restaurant/remove",
    method: "post",
    data: data,
  });
}
