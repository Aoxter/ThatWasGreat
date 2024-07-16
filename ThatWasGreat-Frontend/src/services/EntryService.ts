import httpClient from "../http-common";
import Entry from "../types/Entry";

const getAll = () => {
  return httpClient.get<Array<Entry>>("/entry/all");
};

const getAllInCategory = (categoryId: number) => {
    return httpClient.get<Array<Entry>>(`/entry/all?categoryId=${categoryId}`);
  };

const get = (id: number) => {
  return httpClient.get<Entry>(`/entry/${id}`);
};

const create = (categoryId: number, data: Entry) => {
  return httpClient.post<Entry>(`/entry/new?categoryId=${categoryId}`, data);
};

const update = (id: number, data: Entry) => {
  return httpClient.put<any>(`/entry/${id}/edit`, data);
};

const remove = (id: number) => {
  return httpClient.delete<any>(`/entry/${id}/delete`);
};

const EntryService = {
  getAll,
  getAllInCategory,
  get,
  create,
  update,
  remove,
};

export default EntryService;