import Vue from 'vue';
import lodash from 'lodash';
import VueRouter from 'vue-router';
import store from '@/store';

Vue.use(VueRouter);

const routes = [
  {
    path: '/manage',
    component: () => import('@/components/layout/ManageLayout.vue'),
    meta: {
      requireAuth: false
    },
    children: [
      {
        path: 'merchantManage',
        component: () => import('@/views/maintenance/merchantManage/MerchantManage.vue'),
        meta: {
          requireAuth: true
        }
      },
      {
        path: 'userManage',
        component: () => import('@/views/maintenance/userManage/UserManage.vue'),
        meta: {
          requireAuth: true
        }
      },
      {
        path: 'merchantInfo',
        component: () => import('@/views/maintenance/merchantInfo/MerchantInfo.vue'),
        meta: {
          requireAuth: true
        }
      },
      {
        path: 'userInfo',
        component: () => import('@/views/maintenance/userInfo/UserInfo.vue'),
        meta: {
          requireAuth: true
        }
      },
      {
        path: 'header',
        component: () => import('@/views/maintenance/header/Header.vue'),
        meta: {
          requireAuth: true
        }
      },
      {
        path: 'merchantPassword',
        component: () => import('@/views/maintenance/merchantPassword/MerchantPassword.vue'),
        meta: {
          requireAuth: true
        }
      },
      {
        path: 'userPassword',
        component: () => import('@/views/maintenance/userPassword/UserPassword.vue'),
        meta: {
          requireAuth: true
        }
      },
      {
        path: 'dictionary',
        component: () => import('@/views/maintenance/dictionary/Dictionary.vue'),
        meta: {
          requireAuth: true
        }
      },
      {
        path: 'sportGroundManage',
        component: () => import('@/views/maintenance/sportGround/SportGroundManage.vue'),
        meta: {
          requireAuth: true
        }
      },
      {
        path: 'orderManage',
        component: () => import('@/views/maintenance/orderManage/OrderManage.vue'),
        meta: {
          requireAuth: true
        }
      }
    ]
  },
  {
    path: '/feature',
    component: () => import('@/components/layout/FeatureLayout.vue'),
    meta: {
      requireAuth: false
    },
    children: [
      {
        path: 'home',
        component: () => import('@/views/feture/home/Home.vue'),
        meta: {
          requireAuth: false
        },
        children: [
          {
            name: 'reservation',
            path: 'reservation',
            component: () => import('@/views/feture/reservation/Reservation.vue'),
            meta: {
              requireAuth: false
            }
          },
          {
            path: 'homeBody',
            component: () => import('@/views/feture/home/HomeBody.vue'),
            meta: {
              requireAuth: false
            }
          },
          {
            path: 'merchantRegister',
            component: () => import('@/views/feture/merchant/Register.vue'),
            meta: {
              requireAuth: false
            }
          }
        ]
      },

      {
        path: 'register',
        component: () => import('@/views/feture/register/Register.vue'),
        meta: {
          requireAuth: false
        }
      },
      {
        path: 'login',
        component: () => import('@/views/feture/login/Login.vue'),
        meta: {
          requireAuth: false
        }
      },
      {
        path: 'manage-login',
        component: () => import('@/views/feture/login/Login.vue'),
        meta: {
          requireAuth: false
        }
      }
    ]
  }
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

router.beforeEach((to, from, next) => {
  if (to.fullPath !== '/feature/login' && to.fullPath !== '/feature/register') {
    localStorage.setItem('preRoute', to.fullPath);
  }
  const token = localStorage.getItem('token');
  if (lodash.isEmpty(token)) {
    store.commit('SET_IS_LOGIN', false);
    store.commit('SET_IS_NAME', null);
    store.commit('SET_TYPE', null);
    store.commit('SET_HEADER', null);
    if (to.meta.requireAuth) {
      next({ path: '/feature/login' });
    }
  }
  next();
});

export default router;
