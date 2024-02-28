import request from "@/utils/request";

export function list(query) {
  return request({
    url: "/restaurant/recommended/list",
    method: "get",
    params: query,
  });
}

export function recommend(query) {
  return request({
    url: "/restaurant/recommended/recommend",
    method: "get",
    params: query,
  });
}

export function add(data) {
  return request({
    url: "/restaurant/recommended/add",
    method: "post",
    data: data,
  });
}

export function selectTSalestableListById(id) {
  return request({
    url: "/restaurant/recommended/selectTSalestableListById/" + id,
    method: "get",
  });
}

export function edit(data) {
  return request({
    url: "/restaurant/recommended/edit",
    method: "post",
    data: data,
  });
}

export function remove(data) {
  return request({
    url: "/restaurant/recommended/remove",
    method: "post",
    data: data,
  });
}

export function timeTasks() {
  return request({
    url: "/restaurant/recommended/timedTasks",
    method: "post",
  });
}

export function listMenuTree(data) {
  return request({
    url: "/restaurant/recommended/listTree",
    method: "post",
    data: data,
  });
}

export function listPrice(query) {
  return request({
    url: "/restaurant/recommended/listPrice",
    method: "get",
    params: query,
  });
}