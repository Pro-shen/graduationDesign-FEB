import request from "@/utils/request";

export function add(data) {
    return request({
      url: "/restaurant/checkin/add",
      method: "post",
      data: data,
    });
  }