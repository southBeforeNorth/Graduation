import { del, get, post } from '@/common/util/http.util';

const findAllDictionary = async () => {
  const findAllDictionaryUrl = '/dictionary';
  const result = await get(findAllDictionaryUrl);
  return result.data;
};

const createDictionary = async (param) => {
  const createDictionaryUrl = '/dictionary';
  const result = await post(createDictionaryUrl, param);
  return result.data;
};

const deleteDictionary = async (propertyGroupID) => {
  const deleteDictionaryUrl = `/dictionary/${propertyGroupID}`;
  const result = await del(deleteDictionaryUrl);
  return result.data;
};

const deleteDictionaryOption = async (dictionaryOptionID) => {
  const deleteDictionaryOptionUrl = `/dictionary/option/${dictionaryOptionID}`;
  const result = await del(deleteDictionaryOptionUrl);
  return result.data;
};

const updateDictionary = async (dictionaryID, param) => {
  const updateDictionaryUrl = `/dictionary/${dictionaryID}`;
  const result = await post(updateDictionaryUrl, param);
  return result.data;
};
const getDictionaryOptionListByGroupID = async (dictionaryName) => {
  const getDictionaryOptionListUrl = `/dictionary/${dictionaryName}`;
  const result = await get(getDictionaryOptionListUrl);
  return result.data;
};

const getDictionaryOption = async (dictionary) => {
  const getDictionaryOptionListUrl = `/dictionary/${dictionary.dictionaryName}`;
  const result = await get(getDictionaryOptionListUrl);
  return result.data.find((n) => n.key === dictionary.dictionaryOptionKey);
};

const getMultipleDictionaries = async (dictionaryNames) => {
  const url = `/dictionary/multiple/${dictionaryNames}`;
  const result = await get(url);
  return result.data;
};

export default {
  findAllDictionary,
  createDictionary,
  deleteDictionary,
  updateDictionary,
  deleteDictionaryOption,
  getDictionaryOptionListByGroupID,
  getMultipleDictionaries,
  getDictionaryOption
};
