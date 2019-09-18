const path = require('path');
const VueLoaderPlugin = require('vue-loader/lib/plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin')
const webpack = require('webpack');

//webpack.config.js文件定义参考官网链接
// https://www.webpackjs.com/concepts/output/

const isDev = process.env.NODE_ENV === 'development';

const config = {
    target: 'web',
    entry: path.join(__dirname, "src/index.js"),
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'dist')
    },
    module: {
        rules: [
            {
                test: /\.css$/,
                use: [
                    {
                        loader: 'css-loader'
                    }
                ]
            },
            {
                test: /\.vue$/,
                use: 'vue-loader'
            },
            {
                test: /\.(jpg|gif|jpeg|png)$/,
                use: [
                    {
                        loader: 'url-loader',
                        options: {
                            limit: 1024
                        }
                    }
                ]
            }

        ]
    },
    plugins: [
        new webpack.DefinePlugin({
            'process.env': {
                //判断环境，区分环境打包
                // need to pass as "development"
                NODE_ENV: isDev ? '"development"' : '"production"'
            }
        }),
        // 请确保引入这个插件来施展魔法
        new VueLoaderPlugin(),
        //
        // html插件
        new HtmlWebpackPlugin()
    ]
};

if (isDev) {
    config.devtool = '#cheap-module-eval-source-map';
    config.devServer = {
        host: '0.0.0.0',
        port: 8181,
        overlay: {
            errors: true
        },
        hot: true
        // open: true
    };
    config.plugins.push(
        new webpack.HotModuleReplacementPlugin(),
        new webpack.NoEmitOnErrorsPlugin()
    )
}

module.exports = config;