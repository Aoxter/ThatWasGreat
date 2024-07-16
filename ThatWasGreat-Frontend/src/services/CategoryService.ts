import httpClient from "../http-common";
import Category from "../types/Category";

const getAll = () => {
  return httpClient.get<Array<Category>>("/category/all");
};

const get = (id: number) => {
  return httpClient.get<Category>(`/category/${id}`);
};

const create = (data: Category) => {
  return httpClient.post<Category>("/category/new", data);
};

const update = (id: number, data: Category) => {
  return httpClient.put<any>(`/category/${id}/edit`, data);
};

const remove = (id: number) => {
  return httpClient.delete<any>(`/category/${id}/delete`);
};

const CategoryService = {
  getAll,
  get,
  create,
  update,
  remove,
};

export default CategoryService;