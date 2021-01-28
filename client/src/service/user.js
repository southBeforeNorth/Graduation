// eslint-disable-next-line import/no-cycle
import { get, post } from '@/common/util/http.util';

const contextPath = '/user';
const getUserById = async () => {
  const result = await get(`${contextPath}/userId`);
  return result.data;
};
const createUser = async (params) => {
  const result = await post(`${contextPath}/create`, params);
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
export default {
  getUserById,
  createUser,
  login,
  getUserNameList
};
