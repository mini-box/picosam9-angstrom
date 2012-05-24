COMPATIBLE_MACHINE = "picosam9"

require linux.inc

DESCRIPTION = "Linux kernel for the picosam9 board"

SRCREV = "6cc85e3dccc9b078c0469980fb577b8d2ddc61bc"

#SRC_URI = "git://gitorious.org/picopc-kernel/kernel.git;protocol=git;branch=minibox-picopc-android-3.1" #Fails fetch
SRC_URI = "git://github.com/mini-box/picosam9-kernel.git;protocol=git;branch=minibox-picopc-android-3.1"


LINUX_VERSION ?= "3.1"
PV = "${LINUX_VERSION}+${PR}+git${SRCREV}"

S = "${WORKDIR}/git"

KERNEL_DEFCONFIG = "picosam9g45_defconfig"

# mem=128M@xxxx - picoSAM9 has 2x128Mb memory banks at different adresses and uses
#                 sparsemem memory model to use both banks
# rootwait=1  - wait for the root device to show up (picosam9 boots from microSD(MMC))
# rootdelay=1 - wait 1 second before mounting the root fs (some delay exists between
#               root device showing up and partition detection)
# androidboot.hardware=picopc - compatibility option for booting android
#                               (loading of init.picopc.rc and libhardware libs)
# init=/init  - compatibility option for starting android init.If not found standard /linuxrc is
#               executed
CMDLINE = "mem=128M@0x20000000 mem=128M@0x70000000 console=ttyS0,115200 root=/dev/mmcblk0p2 rw init=/init rootdelay=1 rootwait=1 loglevel=7 androidboot.hardware=picopc" 

do_configure_prepend() {
	install -m 0644 ${S}/arch/${ARCH}/configs/${KERNEL_DEFCONFIG} ${WORKDIR}/defconfig || die "No default configuration for ${MACHINE} / ${KERNEL_DEFCONFIG} available."
}
