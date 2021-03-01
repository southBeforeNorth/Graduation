import {
  upload, get
} from '@/common/util/http.util';

const contextPath = '/picture';

const uploadSportGroundPicture = async (params) => {
  const result = await upload(contextPath, params);
  return result.data;
};

const getRequestAttachments = async (url, formNumber) => {
  const result = await get(`${contextPath}${url}`, { formNumber });
  return result.data;
};

export default {
  uploadSportGroundPicture,
  getRequestAttachments
};
