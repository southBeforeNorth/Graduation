import moment from 'moment';
import momentZone from 'moment-timezone';

export default {
  dateTime(text, defaultContent) {
    if (text) {
      return moment(text).format('MM/DD/YYYY HH:mm');
    }
    if (defaultContent) {
      return defaultContent;
    }
    return text;
  },
  date(text, defaultContent) {
    if (text) {
      return moment(text).format('MM/DD/YYYY');
    }
    if (defaultContent) {
      return defaultContent;
    }
    return text;
  },
  momentDate(text) {
    if (text) {
      return moment(text, 'MM/DD/YYYY');
    }
    return moment();
  },
  momentDateTime(text) {
    if (text) {
      return moment(text, 'MM/DD/YYYY HH:mm');
    }
    return moment();
  },
  momentZoneTime(date, zone) {
    return momentZone.tz(date, zone).format('HH:mm');
  }
};
