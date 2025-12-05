import axios from "./axios";

export const getTags = () =>
  axios.get("/tags").then((r) => r.data);

export const createTag = (data) =>
  axios.post("/tags", data).then((r) => r.data);

export const deleteTag = (id) =>
  axios.delete(`/tags/${id}`);
