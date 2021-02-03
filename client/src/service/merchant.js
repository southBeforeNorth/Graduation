// eslint-disable-next-line import/no-cycle
import { get, post } from '@/common/util/http.util';

const contextPath = '/merchant';

const createMerchant = async (params) => {
  const result = await post(`${contextPath}/create`, params);
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
export default {
  createMerchant,
  login,
  getMerchantNameList
};
