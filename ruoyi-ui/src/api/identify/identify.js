import request from "@/utils/request";

export function list(query) {
  return request({
    url: "/identify/identify/list",
    method: "get",
    params: query,
  });
}