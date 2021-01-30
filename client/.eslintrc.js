module.exports = {
  root: true,
  env: {
    node: true,
  },
  extends: [
    'plugin:vue/essential',
    '@vue/airbnb',
  ],
  parserOptions: {
    parser: 'babel-eslint',
  },
  rules: {
    "template-curly-spacing" : "off",
    "comma-dangle": [
      "error",
      "never"
    ],
    "linebreak-style": 0,
    "semi": [
      "error",
      "always"
    ],
    "no-console": 0,
    "no-trailing-spaces": 0,
    "import/named": 2,
    "consistent-return": 0,
    "no-plusplus": 0,
    "prefer-destructuring": 0
  }
};
