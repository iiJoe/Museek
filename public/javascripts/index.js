function search() {
    const titleInput = document.getElementById("title").value;
    const circleInput = document.getElementById("circle").value;
    const originalInput = document.getElementById("original").value;
    //TODO song filtering
    alert(`Title = ${titleInput} \nCircle = ${circleInput}\nOriginal = ${originalInput}`);
}

$('table').tablesort();

$('.message .close')
    .on('click', function() {
        $(this)
            .closest('.message')
            .transition('fade')
        ;
    })
;

function deleteModal(id) {
    $(`#delete-modal-${id}`).modal('show');
}
