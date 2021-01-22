import axios from 'axios';
import store from '@/store';
import lodash from 'lodash';

export const http = axios.create({
  timeout: 120000,
  baseURL: 'http://localhost:8082'
});
http.interceptors.request.use(
  (config) => {
    if (!lodash.isEmpty(store.state.token)) {
      // eslint-disable-next-line no-param-reassign
      config.headers.Authorization = store.state.token;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export const get = async (url, params) => http.get(url, {
  params: {
    ...params
  }
});

export const post = async (url, params) => http.request({
  url,
  method: 'POST',
  data: params
});

export const del = async (url, params) => http.delete(url, {
  params: {
    ...params
  }
});

export const put = async (url, data) => http.request({
  url,
  method: 'PUT',
  data
});
