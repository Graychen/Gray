<?php 
/** 
 * ģ���� 
 * @copyright   Copyright(c) 2011 
 * @author      yuansir <yuansir@live.cn/yuansir-web.com> 
 * @version     1.0 
 */ 
final class Template { 
        public $template_name = null; 
        public $data = array(); 
        public $out_put = null; 
        
        public function init($template_name,$data = array()) { 
                $this->template_name = $template_name; 
                $this->data = $data; 
                $this->fetch(); 
        } 
        /** 
         * ����ģ���ļ� 
         * @access      public 
         * @param       string  $file 
         */ 
        public function fetch() { 
                $view_file = VIEW_PATH . '/' . $this->template_name . '.php'; 
                if (file_exists($view_file)) { 
                        extract($this->data); 
                        ob_start(); 
                        include $view_file; 
                        $content = ob_get_contents(); 
                        ob_end_clean(); 
                        $this->out_put =  $content; 
                } else { 
                        trigger_error('���� ' . $view_file . ' ģ�岻����'); 
                } 
        } 
        /** 
         * ���ģ�� 
         * @access      public  
         * @return      string 
         */ 
        public function outPut(){ 
                echo $this->out_put; 
        } 
}