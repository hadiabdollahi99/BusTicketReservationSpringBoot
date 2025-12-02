// فرم‌های تاریخ فارسی
document.addEventListener('DOMContentLoaded', function() {
    // تنظیم حداقل تاریخ برای فیلدهای تاریخ
    const today = new Date().toISOString().split('T')[0];
    const dateInputs = document.querySelectorAll('input[type="date"]');
    dateInputs.forEach(input => {
        input.min = today;
    });



    // تایید لغو بلیط
    const cancelForms = document.querySelectorAll('.cancel-form');
    cancelForms.forEach(form => {
        form.addEventListener('submit', function(e) {
            if (!confirm('آیا از لغو این بلیط اطمینان دارید؟ این عمل قابل بازگشت نیست.')) {
                e.preventDefault();
            }
        });
    });

    // نمایش پیام‌ها به صورت موقت
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.opacity = '0';
            setTimeout(() => {
                if (alert.parentNode) {
                    alert.parentNode.removeChild(alert);
                }
            }, 500);
        }, 5000);
    });
});