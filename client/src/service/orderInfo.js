// eslint-disable-next-line import/no-cycle
import {
  post, get, del, put
} from '@/common/util/http.util';

const contextPath = '/orderInfo';

const getSportGroundsById = async (params) => {
  const result = await get(`${contextPath}/pageById`, params);
  return result.data;
};

const createOrder = async (params) => {
  const result = await post(`${contextPath}/create`, params);
  return result.data;
};

const getOrderInfoBySportGroundId = async (id, param) => {
  const result = await get(`${contextPath}/getBySportGroundId/${id}`, param);
  return result.data;
};

const getSportGroundList = async (params) => {
  const result = await get(`${contextPath}/page`, params);
  return result.data;
};

const deleteSportGround = async (Id) => {
  const result = await del(`${contextPath}/delete/${Id}`);
  return result.data;
};

const updateSportGround = async (id, params) => {
  const result = await put(`${contextPath}/update/${id}`, params);
  return result.data;
};

export default {
  createOrder,
  getSportGroundsById,
  deleteSportGround,
  updateSportGround,
  getSportGroundList,
  getOrderInfoBySportGroundId
};
