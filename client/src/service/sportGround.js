// eslint-disable-next-line import/no-cycle
import {
  post, get, del, put
} from '@/common/util/http.util';

const contextPath = '/sportGround';

const createSportGround = async (params) => {
  const result = await post(`${contextPath}/create`, params);
  return result.data;
};
const getSportGroundsById = async (params) => {
  const result = await get(`${contextPath}/pageById`, params);
  return result.data;
};

const getSportGroundById = async (id) => {
  const result = await get(`${contextPath}/get/${id}`);
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
  createSportGround,
  getSportGroundsById,
  deleteSportGround,
  updateSportGround,
  getSportGroundList,
  getSportGroundById
};
