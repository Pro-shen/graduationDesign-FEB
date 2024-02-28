import request from "@/utils/request";

export function list(query) {
  return request({
    url: "/identify/identify/list",
    method: "get",
    params: query,
  });
}

export function upload(data) {
  return request({
    url: "/identify/identify/upload",
    method: "post",
    data: data,
  });
}

export function addList(data) {
  return request({
    url: "/identify/identify/addList",
    method: "post",
    data: data,
  });
}

export function listMenuTree(data) {
  return request({
    url: "/identify/identify/listTree",
    method: "post",
    data: data,
  });
}

export function listPrice(query) {
  return request({
    url: "/identify/identify/listPrice",
    method: "get",
    params: query,
  });
}
