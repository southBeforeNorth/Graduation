// eslint-disable-next-line import/no-cycle
import {
  post, get, del, put
} from '@/common/util/http.util';

const contextPath = '/orderInfo';

const createOrder = async (params) => {
  const result = await post(`${contextPath}/create`, params);
  return result.data;
};

const getOrderInfoBySportGroundId = async (id, param) => {
  const result = await get(`${contextPath}/getBySportGroundId/${id}`, param);
  return result.data;
};

const getOrderListByPage = async (params) => {
  const result = await get(`${contextPath}/getByPage`, params);
  return result.data;
};

const changeOrderStatus = async (id, params) => {
  const result = await put(`${contextPath}/changeStatus/${id}`, params);
  return result.data;
};

const createComment = async (id, params) => {
  const result = await put(`${contextPath}/createComment/${id}`, params);
  return result.data;
};

const deleteOrderById = async (Id) => {
  const result = await del(`${contextPath}/delete/${Id}`);
  return result.data;
};

const deleteCommentById = async (params) => {
  const result = await del(`${contextPath}/deleteComment`, params);
  return result.data;
};

const updateSportGround = async (id, params) => {
  const result = await put(`${contextPath}/update/${id}`, params);
  return result.data;
};

const getCommentListById = async (id) => {
  const result = await get(`${contextPath}/getCommentList/${id}`);
  return result.data;
};

const getCommentByPageById = async (id, params) => {
  const result = await get(`${contextPath}/getCommentByPage/${id}`, params);
  return result.data;
};
export default {
  createOrder,
  getOrderListByPage,
  changeOrderStatus,
  deleteOrderById,
  updateSportGround,
  getOrderInfoBySportGroundId,
  createComment,
  deleteCommentById,
  getCommentListById,
  getCommentByPageById
};
