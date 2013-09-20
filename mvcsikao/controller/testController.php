<?php
class testController extends Controller{

	public function __construct(){
		parent::__construct();
	}
	
	  public function index() { 
                $data['test'] = "yuansir-web.com"; 
                $this->showTemplate('test', $data); 
        } 
	
	public function testDb(){
		$modTest= $this->model("test");
		$databases=$modTest->testDatebases();
		var_dump($databases);
	}
	
	
}