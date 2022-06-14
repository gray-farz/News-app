<?php

$page=isset($_GET['page'])? $_GET['page']:1;

$records_per_page=isset($_GET['per_page'])? $_GET['per_page']:5;

$from_record_num=($records_per_page*$page)-$records_per_page;

include"database.php";

class Product{
    
    private $conn;
    private $tablename="tbl_news";
    public function __construct($db){
        $this->conn=$db;
    }
    
    public function readPaging($from_record_num,$records_per_page){
        
        $filter=$_GET['filter'];
        $name=$_GET['name'];
        $befor=$_GET['befor'];
        $afetr=$_GET['after'];
        
        $lang=$_SERVER['HTTP_ACCEPT_LANGUAGE'];
        
        if($lang=='en'){
            
            switch($filter){
                //دسته
                case "cat":
                      $query="SELECT c.cat_image,c.en_cat as cat,p.id,p.en_title as title,p.image,p.en_date as date 
                      FROM tbl_news p
                      LEFT JOIN tbl_cat c ON p.cat_id =c.id
                      WHERE p.cat_id ='$name' ORDER BY p.id DESC LIMIT ? ,?"; 
                    break;
                    
                //تاریخ
                case "date":
                      $query="SELECT c.cat_image,c.en_cat as cat,p.id,p.en_title as title,p.image,p.en_date as date 
                      FROM tbl_news p
                      LEFT JOIN tbl_cat c ON p.cat_id =c.id
                      WHERE p.en_date BETWEEN '$befor' AND '$afetr' ORDER BY p.id DESC LIMIT ? ,?"; 
                    break;
                    
                default:
                      $query="SELECT c.cat_image,c.en_cat as cat,p.id,p.en_title as title,p.image,p.en_date as date 
                      FROM tbl_news p
                      LEFT JOIN tbl_cat c ON p.cat_id =c.id
                      ORDER BY p.id DESC LIMIT ? ,?"; 
                    break;
            }
            
        }else{
            
            switch($filter){
                //دسته
                case "cat":
                      $query="SELECT c.cat_image,c.fa_cat as cat,p.id,p.fa_title as title,p.image,p.fa_date as date
                      FROM tbl_news p
                      LEFT JOIN tbl_cat c ON p.cat_id =c.id
                      WHERE p.cat_id ='$name' ORDER BY p.id DESC LIMIT ? ,?"; 
                    break;
                    
                //تاریخ
                case "date":
                      $query="SELECT c.cat_image,c.fa_cat as cat,p.id,p.fa_title as title,p.image,p.fa_date as date
                      FROM tbl_news p
                      LEFT JOIN tbl_cat c ON p.cat_id =c.id
                      WHERE p.fa_date BETWEEN '$befor' AND '$afetr' ORDER BY p.id DESC LIMIT ? ,?"; 
                    break;
                    
                default:
                    $query="SELECT c.cat_image,c.fa_cat as cat,p.id,p.fa_title as title,p.image,p.fa_date as date
                    FROM tbl_news p
                    LEFT JOIN tbl_cat c ON p.cat_id =c.id
                    ORDER BY p.id DESC LIMIT ? ,?"; 
                    break;
            }
            
        }
        
        $stmt=$this->conn->prepare($query);
        
        $stmt->bindParam(1,$from_record_num,PDO::PARAM_INT);
        $stmt->bindParam(2,$records_per_page,PDO::PARAM_INT);
        
        $stmt->execute();
        return $stmt;
    }
    
    public function count(){
       
        $filter=$_GET['filter'];
        $name=$_GET['name'];
        $befor=$_GET['befor'];
        $afetr=$_GET['after'];
        
        $lang=$_SERVER['HTTP_ACCEPT_LANGUAGE'];
        
        if($lang=='en'){
            
            switch($filter){
                //دسته
                case "cat":
                      $query="SELECT c.cat_image,c.en_cat as cat,p.id,p.en_title as title,p.image,p.en_date as date 
                      FROM tbl_news p
                      LEFT JOIN tbl_cat c ON p.cat_id =c.id
                      WHERE p.cat_id ='$name' ORDER BY p.id DESC LIMIT ? ,?"; 
                    break;
                    
                //تاریخ
                case "date":
                      $query="SELECT c.cat_image,c.en_cat as cat,p.id,p.en_title as title,p.image,p.en_date as date 
                      FROM tbl_news p
                      LEFT JOIN tbl_cat c ON p.cat_id =c.id
                      WHERE p.en_date BETWEEN '$befor' AND '$afetr' ORDER BY p.id DESC LIMIT ? ,?"; 
                    break;
                    
                default:
                      $query="SELECT c.cat_image,c.en_cat as cat,p.id,p.en_title as title,p.image,p.en_date as date 
                      FROM tbl_news p
                      LEFT JOIN tbl_cat c ON p.cat_id =c.id
                      ORDER BY p.id DESC LIMIT ? ,?"; 
                    break;
            }
            
        }else{
            
                   switch($filter){
                //دسته
                case "cat":
                      $query="SELECT c.cat_image,c.fa_cat as cat,p.id,p.fa_title as title,p.image,p.fa_date as date
                      FROM tbl_news p
                      LEFT JOIN tbl_cat c ON p.cat_id =c.id
                      WHERE p.cat_id ='$name' ORDER BY p.id DESC LIMIT ? ,?"; 
                    break;
                    
                //تاریخ
                case "date":
                      $query="SELECT c.cat_image,c.fa_cat as cat,p.id,p.fa_title as title,p.image,p.fa_date as date
                      FROM tbl_news p
                      LEFT JOIN tbl_cat c ON p.cat_id =c.id
                      WHERE p.fa_date BETWEEN '$befor' AND '$afetr' ORDER BY p.id DESC LIMIT ? ,?"; 
                    break;
                    
                default:
                    $query="SELECT c.cat_image,c.fa_cat as cat,p.id,p.fa_title as title,p.image,p.fa_date as date
                    FROM tbl_news p
                    LEFT JOIN tbl_cat c ON p.cat_id =c.id
                    ORDER BY p.id DESC LIMIT ? ,?"; 
                    break;
            }
            
        }
        
        $stmt=$this->conn->prepare($query);
        $stmt->execute();
        $row=$stmt->fetch(PDO::FETCH_ASSOC);
        
        return $row['total_rows'];
        
    }
    
}
    
    $database=new Database();
    $db=$database->getConnection();
    
    $product=new Product($db);
    
    $stmt=$product->readPaging($from_record_num,$records_per_page);
    $num=$stmt->rowCount();
    
    if($num>0){
        
        while( $row=$stmt->fetch(PDO::FETCH_ASSOC)){
            
            $record["id"]=(int)$row['id'];
            $record["title"]=$row['title'];
            $record["image"]=$row['image'];
            $record["date"]=$row['date'];
            $record["cat"]=$row['cat'];
            $record["cat_image"]=$row['cat_image'];
            
            $statistic[]=$record;
        }
        
        echo json_encode($statistic);
        
    }else{
        echo'[]';
    }
    
?>