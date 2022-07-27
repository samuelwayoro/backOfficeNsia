$(function() {
    $('#jMaxDemandeSoldeField').change(function(){
        console.log('changed');
        $(this).checked = !($(this).checked);
    });
});
