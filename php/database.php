<?php

class Database{
    
    private $host="localhost";
    private $username="rezaeia3_news";
    private $password="2623286Gray";
    private $db_name="rezaeia3_news";
    public $conn;
    
    public function getConnection(){
        
        $this->conn=null;
        
        try{
            $this->conn=new PDO ("mysql:host=".$this->host.";dbname=".$this->db_name,$this->username,$this->password);
            $this->conn->exec("set names utf8");
            
        }catch(PDOException $exception){
            echo"connection error".$exception->getMassage();
        }
        
        return $this->conn;
    }
   
}

?>