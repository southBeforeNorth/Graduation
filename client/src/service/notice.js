// eslint-disable-next-line import/no-cycle
import {
  get,
  post
} from '@/common/util/http.util';

const contextPath = '/notice';

const updateNotice = async (params) => {
  const result = await post(`${contextPath}/update`, params);
  return result.data;
};

const getNotice = async () => {
  const result = await get(`${contextPath}/get`);
  return result.data;
};
export default {
  updateNotice,
  getNotice
};
