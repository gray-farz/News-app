<?php

include "connection.php";

$query1 = "SELECT en_title as title, en_content as content, image, en_date as date FROM tbl_news";
$result1 = $connect->prepare($query1);
$result1-> execute();

if($result1->rowCount() > 0)
{

    while($row = $result1->fetch(PDO::FETCH_ASSOC))
    {
        $record["title"]=$row['title'];
        $record["content"]=$row['content'];
        $record["image"]=$row['image'];
        $record["date"]=$row['date'];

        $jsonResult[] = $record;

    }

    echo json_encode($jsonResult);
}
else{
    echo '[]';
}

?>
