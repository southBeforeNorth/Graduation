import Vue from 'vue';
// eslint-disable-next-line import/no-cycle
import userService from '@/service/user';
import lodash from 'lodash';
import store from '@/store';
import VueRouter from 'vue-router';

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
        path: 'user',
        component: () => import('@/views/maintenance/user/User.vue'),
        meta: {
          requireAuth: false
        }
      },
      {
        path: 'dictionary',
        component: () => import('@/views/maintenance/dictionary/Dictionary.vue'),
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
  if (to.meta.requireAuth) {
    const token = localStorage.getItem('token');
    localStorage.setItem('preRoute', to.fullPath);
    if (lodash.isEmpty(token)) {
      next('feature/login');
    } else {
      if (!store.state.user.isLogin) {
        userService.getUserById().then((n) => {
          if (n.success) {
            console.log(n.data);
          }
        });
      }
      next();
    }
  } else {
    next();
  }
});

export default router;
