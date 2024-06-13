import http from "../http-common";
import ICategoryData from "../types/Category";

const getAll = () => {
  return http.get<Array<ICategoryData>>("/category/all");
};

const get = (id: any) => {
  return http.get<ICategoryData>(`/category/${id}`);
};

const create = (data: ICategoryData) => {
  return http.post<ICategoryData>("/category/new", data);
};

const update = (id: any, data: ICategoryData) => {
  return http.put<any>(`/category/${id}/edit`, data);
};

const remove = (id: any) => {
  return http.delete<any>(`/category/${id}/delete`);
};

// const findByTitle = (title: string) => {
//   return http.get<Array<ITutorialData>>(`/tutorials?title=${title}`);
// };

const CategoryService = {
  getAll,
  get,
  create,
  update,
  remove,
};

export default CategoryService;