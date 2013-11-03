<?php 
/** 
 * ����ģ���� 
 * @copyright   Copyright(c) 2011 
 * @author      yuansir <yuansir@live.cn/yuansir-web.com> 
 * @version     1.0 
 */ 
class Model { 
        protected $db = null; 
        
        final public function __construct() { 
                header('Content-type:text/html;chartset=utf-8'); 
                $this->db = $this->load('mysql'); 
                $config_db = $this->config('db'); 
                $this->db->init( 
                        $config_db['db_host'], 
                        $config_db['db_user'], 
                        $config_db['db_password'], 
                        $config_db['db_database'], 
                        $config_db['db_conn'], 
                        $config_db['db_charset'] 
                        );                                            //��ʼ�����ݿ��� 
        } 
        /** 
         * ���ݱ�ǰ׺��ȡ���� 
         * @access      final   protected 
         * @param       string  $table_name    ���� 
         */ 
        final protected function table($table_name){ 
                $config_db = $this->config('db'); 
                return $config_db['db_table_prefix'].$table_name; 
        } 
        /** 
         * ������� 
         * @param string $lib   ������� 
         * @param Bool  $my     ���FALSEĬ�ϼ���ϵͳ�Զ����ص���⣬���ΪTRUE������Զ������ 
         * @return type 
         */ 
        final protected function load($lib,$my = FALSE){ 
                if(empty($lib)){ 
                        trigger_error('�������������Ϊ��'); 
                }elseif($my === FALSE){ 
                        return Application::$_lib[$lib]; 
                }elseif($my === TRUE){ 
                        return  Application::newLib($lib); 
                } 
        } 
        /** 
         * ����ϵͳ����,Ĭ��Ϊϵͳ���� $CONFIG['system'][$config] 
         * @access      final   protected 
         * @param       string  $config ������  
         */ 
        final   protected function config($config=''){ 
                return Application::$_config[$config]; 
        } 
} 