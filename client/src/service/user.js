// eslint-disable-next-line import/no-cycle
import {
  get,
  post,
  put,
  del
} from '@/common/util/http.util';

const contextPath = '/user';
const getUserById = async () => {
  const result = await get(`${contextPath}/userId`);
  return result.data;
};
const createUser = async (params) => {
  const result = await post(`${contextPath}/create`, params);
  return result.data;
};

const updateUserByManager = async (id, params) => {
  const result = await put(`${contextPath}/updateByManager/${id}`, params);
  return result.data;
};

const login = async (params) => {
  const result = await post(`${contextPath}/login`, params);
  return result.data;
};
const getUserNameList = async () => {
  const result = await get(`${contextPath}/nameList`);
  return result.data;
};

const uploadUserHeader = async (id) => {
  const result = await put(`${contextPath}/uploadHeader/${id}`);
  return result.data;
};

const updateUserByOwner = async (params) => {
  const result = await put(`${contextPath}/updateByOwner`, params);
  return result.data;
};

const changeUserPasswordByOwner = async (params) => {
  const result = await get(`${contextPath}/changePassword`, params);
  return result.data;
};

const getUserByPage = async (params) => {
  const result = await get(`${contextPath}/page`, params);
  return result.data;
};

const deleteUser = async (Id) => {
  const result = await del(`${contextPath}/delete/${Id}`);
  return result.data;
};

export default {
  getUserById,
  createUser,
  login,
  getUserNameList,
  uploadUserHeader,
  updateUserByOwner,
  changeUserPasswordByOwner,
  getUserByPage,
  deleteUser,
  updateUserByManager
};
