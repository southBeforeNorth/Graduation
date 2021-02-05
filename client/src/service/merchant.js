// eslint-disable-next-line import/no-cycle
import {
  put, del, get, post
} from '@/common/util/http.util';

const contextPath = '/merchant';

const createMerchant = async (params) => {
  const result = await post(`${contextPath}/create`, params);
  return result.data;
};
const createMerchantByManager = async (params) => {
  const result = await post(`${contextPath}/createByManager`, params);
  return result.data;
};
const login = async (params) => {
  const result = await post(`${contextPath}/login`, params);
  return result.data;
};
const getMerchantNameList = async () => {
  const result = await get(`${contextPath}/nameList`);
  return result.data;
};

const getMerchantByPage = async (params) => {
  const result = await get(`${contextPath}/page`, params);
  return result.data;
};

const deleteMerchant = async (Id) => {
  const result = await del(`${contextPath}/delete/${Id}`);
  return result.data;
};

const updateMerchant = async (id, params) => {
  const result = await put(`${contextPath}/update/${id}`, params);
  return result.data;
};

export default {
  createMerchant,
  login,
  getMerchantNameList,
  getMerchantByPage,
  createMerchantByManager,
  deleteMerchant,
  updateMerchant
};
